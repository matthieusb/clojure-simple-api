(defproject clojure-rest "0.1.0-SNAPSHOT"
  :description "Simple clojure rest service for ippon article"
  :url "https://github.com/matthieusb/clojure-simple-api"
  :min-lein-version "2.0.0"

  :dependencies [[org.clojure/clojure "1.8.0"] ; Clojure version
                 [org.clojure/tools.logging "0.4.0"]
                 [compojure "1.6.0"] ; routing library
                 [cheshire "5.8.0"] ; Json parsing librairy
                 [org.clojure/java.jdbc "0.7.0"] ; jdbc support
                 [com.h2database/h2 "1.4.196"] ; h2 database support
                 [ring/ring-json "0.4.0"] ; ring server json support
                 [ring/ring-defaults "0.3.1"] ; ring server default dependencies
                 [jarohen/nomad "0.7.3"] ; configuration handling library
                 [prismatic/schema "1.1.6"] ; bean validation equivalent for clojure
                 [korma "0.4.3"] ; orm dependency
                 ]

  :plugins [[lein-ring "0.12.0"]
            [lein-pprint "1.1.2"]
            [lein-cloverage "1.0.9"]
            [jonase/eastwood "0.2.4"]]

  :profiles { :test {:resource-paths ["resources/test"]}
              :dev
                    {:resource-paths ["resources/dev"]
                     :dependencies [[javax.servlet/servlet-api "2.5"]
                                    [ring/ring-mock "0.3.1"]
                                    ]}}

  :ring { :handler clojure-rest.handler/app :init clojure-rest.config.init/init-app})
