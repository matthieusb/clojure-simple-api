(defproject clojure-rest "0.1.0-SNAPSHOT"
  :description "Simple clojure rest service for "
  :url "https://github.com/matthieusb/clojure-simple-api"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [c3p0/c3p0 "0.9.1.2"] ; Connection pool
                 [cheshire "4.0.3"] ; Json parsing librairy
                 [com.novemberain/monger "3.0.1"] ; mongodb support
                 [ring/ring-json "0.1.2"]
                 [ring/ring-defaults "0.2.1"]] ; ring server default dependencies
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler clojure-rest.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
