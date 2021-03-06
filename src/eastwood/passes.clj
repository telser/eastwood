(ns eastwood.passes
  (:refer-clojure :exclude [get-method])
  (:require [clojure.string :as str]
            [clojure.tools.analyzer.ast :refer [update-children]]
            [eastwood.util :as util]))

(defmulti reflect-validated :op)

(defn arg-type-str [arg-types]
  (str/join ", "
            (map #(if (nil? %) "nil" (.getName ^Class %)) arg-types)))

(defn get-ctor [ast]
  (let [cls (:class ast)
        arg-type-vec (mapv :tag (:args ast))
        arg-type-arr (into-array Class arg-type-vec)]
;;    (println (format "dbgx: get-ctor cls=%s arg-types=%s"
;;                     cls (arg-type-str arg-type-vec)))
    (try
      (.getConstructor ^Class cls arg-type-arr)
      (catch NoSuchMethodException e
        (try
          (.getDeclaredConstructor ^Class cls arg-type-arr)
          (catch NoSuchMethodException e
            {:class cls, :arg-types arg-type-vec}))))))

(defn get-field [ast]
  (let [cls (:class ast)
        fld-name (name (:field ast))]
    (try
      (.getField ^Class cls fld-name)
      (catch NoSuchFieldException e
        (try
          (.getDeclaredField ^Class cls fld-name)
          (catch NoSuchFieldException e
            {:class cls, :field-name fld-name}))))))

(defn get-method [ast]
  (let [cls (:class ast)
        method-name (name (:method ast))
        arg-type-vec (mapv :tag (:args ast))
        arg-type-arr (into-array Class arg-type-vec)]
;;    (println (format "dbgx: get-method cls=%s method=%s arg-types=%s"
;;                     cls method-name (arg-type-str arg-type-vec)))
    (when (some nil? arg-type-vec)
      (println (format "Error: Bad arg-type nil for method named %s for class %s, full arg type list (%s).  ast pprinted below for debugging tools.analyzer:"
                       method-name
                       (.getName ^Class cls)
                       (arg-type-str arg-type-vec)))
      (util/pprint-ast-node ast))
    (try
      (.getMethod ^Class cls method-name arg-type-arr)
      (catch NoSuchMethodException e
        (try
          (.getDeclaredMethod ^Class cls method-name arg-type-arr)
          (catch NoSuchMethodException e
            {:class cls, :method-name method-name,
             :arg-types arg-type-vec}))))))

(defmethod reflect-validated :default [ast] ast)

(defmethod reflect-validated :new [ast]
  (if (:validated? ast)
    (assoc ast :reflected-ctor (get-ctor ast))
    ast))

(defmethod reflect-validated :instance-field [ast]
  (assoc ast :reflected-field (get-field ast)))

(defmethod reflect-validated :instance-call [ast]
  (if (:validated? ast)
    (assoc ast :reflected-method (get-method ast))
    ast))

(defmethod reflect-validated :static-field [ast]
  (assoc ast :reflected-field (get-field ast)))

(defmethod reflect-validated :static-call [ast]
  (if (:validated? ast)
    (assoc ast :reflected-method (get-method ast))
    ast))

(defmulti propagate-def-name :op)

(defmethod propagate-def-name :default
  [{:keys [env] :as ast}]
  (if-let [def-name (:name env)]
    (update-children ast (fn [ast] (assoc-in ast [:env :name] def-name)))
    ast))

(defmethod propagate-def-name :def
  [{:keys [name] :as ast}]
  (update-children ast (fn [ast] (assoc-in ast [:env :name] name))))
