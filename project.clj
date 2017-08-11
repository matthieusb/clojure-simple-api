(defproject clojure-rest "0.1.0-SNAPSHOT"
  :description "Simple clojure rest service for ippon article"
  :url "https://github.com/matthieusb/clojure-simple-api"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.6.0"]
                 [c3p0/c3p0 "0.9.1.2"] ; Connection pool
                 [cheshire "5.7.1"] ; Json parsing librairy
                 [org.clojure/java.jdbc "0.7.0"] ; jdbc support
                 [com.h2database/h2 "1.4.196"] ; h2 database support
                 [com.novemberain/monger "3.1.0"] ; mongodb support
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.3.1"]] ; ring server default dependencies

  :plugins [[lein-ring "0.12.0"]
            [lein-watch "0.0.3"]]
  :ring {:handler clojure-rest.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.1"]]}}
  :watch { ; Watch compile configuration
           :rate 500 ; check file every 500ms
           :watchers { :compile { :watch-dirs ["src"]
                                  :file-patterns [#"\.clj"]
                                  :tasks ["compile"]}}})
