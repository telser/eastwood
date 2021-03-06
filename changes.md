# Change log for Eastwood

## Changes from version 0.1.1 to 0.1.2


* Updated `tools.analyzer` and `tools.analyzer.jvm` to version
  0.1.0-beta10.  Most of the Eastwood issues fixed since Eastwood
  0.1.1 were due to this change.

* Changed method of analyzing code that was throwing exception with
  some projects, e.g. kria.  Fixes issue
  [#60](https://github.com/jonase/eastwood/issues/60), and I think
  this same fix also corrected issue
  [#54](https://github.com/jonase/eastwood/issues/54)

* Fixed analysis problem that caused Eastwood to throw exceptions when
  analyzing [Midje](https://github.com/marick/Midje), and test code of
  libraries that used Midje.  Fixes issue
  [#61](https://github.com/jonase/eastwood/issues/61)

* New functions and macros added to Clojure 1.6.0 will now cause
  :unused-ret-vals or :unused-ret-vals-in-try warnings if they are
  called and their return value is ignored, just as that happens for
  other functions in clojure.core.  Fixes issue
  [#59](https://github.com/jonase/eastwood/issues/59)

* Updates to scripts and files used to test Eastwood, of interest only
  to Eastwood developers.  Now it is straightforward to ensure that
  you get the same version of project source code if you use the test
  scripts on multiple machines, instead of getting whatever the latest
  happened to be at the time you ran the clone.sh script.


## Changes from version 0.1.0 to 0.1.1


* Added consistency checking between namespace and file names before
  actual linting begins, to avoid hard-to-understand error messages
  that could otherwise result.  See [this
  section](https://github.com/jonase/eastwood/#check-consistency-of-namespace-and-file-names)
  in the docs.

* Added `:bad-arglists` linter.  See [this
  section](https://github.com/jonase/eastwood/#bad-arglists---functionmacro-definitions-with-arg-vectors-differing-from-their-arglists-metadata)
  in the docs.

* No longer issue warnings for code inside of `comment` forms.  Fixes
  issue [#47](https://github.com/jonase/eastwood/issues/47)

* `lein help` now gives one-line description of Eastwood plugin, and
  `lein eastwood help` gives a link to the full documentation, and
  help about the same as that in the "Installation & Quick Usage"
  section.

* Reflection warnings appearing in output of `lein eastwood` should
  now be much closer to those produced by Clojure itself, and usually
  include useful line:column numbers.  There may still be some
  differences, so reflection warnings in the output of `lein check`
  are still the ones you want to trust, if there are any differences.

* Updated `tools.analyzer` and `tools.analyzer.jvm` to version
  0.1.0-beta8.  Updated some Eastwood code as a result of changes in
  those libraries.  (Eastwood version 0.1.0 used version 0.1.0-alpha1
  of those libraries).

* Top level `do` forms are now analyzed by Eastwood similarly to how
  Clojure itself does it, as if the forms inside the `do` were
  themselves independent top level forms.  See the article ["in which
  the perils of the gilardi scenario are
  overcome"](http://technomancy.us/143) for some description of why
  Clojure does this.  Fixes issue
  [#49](https://github.com/jonase/eastwood/issues/49)

* Correctly detect `gen-interface` forms even if invoked using
  `clojure.core/gen-interface`.
  [Link](https://github.com/jonase/eastwood/commit/fa61e5f4400c2fd334b87634c31c5c1270f3b9f6)
  to the commit.

* Updates to scripts and files used to test Eastwood, of interest only
  to Eastwood developers.
