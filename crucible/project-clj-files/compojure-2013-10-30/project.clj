(defproject compojure "1.1.6"
  :description "A concise routing library for Ring"
  :url "https://github.com/weavejester/compojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :global-vars {*warn-on-reflection* true}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.incubator "0.1.0"]
                 [org.clojure/tools.macro "0.1.0"]
                 [clout "1.1.0"]
                 [ring/ring-core "1.2.1"]]
  :plugins [[codox "0.6.6"]]
  :codox {:src-dir-uri "http://github.com/weavejester/compojure/blob/1.1.6"
          :src-linenum-anchor-prefix "L"}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}
   :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
   :1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}
   :1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}
   :1.7 {:dependencies [[org.clojure/clojure "1.7.0-master-SNAPSHOT"]]}})
