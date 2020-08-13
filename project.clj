(defproject clojure-rest "0.1.0-SNAPSHOT"
  :description "Simple clojure rest service for ippon article"
  :url "https://github.com/matthieusb/clojure-simple-api"
  :min-lein-version "2.0.0"

  :dependencies [[org.clojure/clojure "1.8.0"] ; Clojure version
                 [org.clojure/tools.logging "0.4.0"]
                 [compojure "1.6.0"] ; routing library
                 [metosin/compojure-api "1.1.11"] ; improves compojure experience
                 [cheshire "5.8.0"] ; Json parsing librairy
                 [org.clojure/java.jdbc "0.7.1"] ; jdbc support
                 [com.h2database/h2 "1.4.196"] ; h2 database support
                 [ring/ring-json "0.4.0"] ; ring server json support
                 [ring/ring-defaults "0.3.1"] ; ring server default dependencies
                 [jarohen/nomad "0.7.3"] ; configuration handling library
                 [prismatic/schema "1.1.6"] ; bean validation equivalent for clojure
                 [korma "0.4.3"] ; orm dependency
                 [ragtime "0.7.1"] ; migration utility
                 ]

  :plugins [[lein-ring "0.12.1"]
            [lein-pprint "1.1.2"]
            [lein-cloverage "1.0.9"] ; Coverage for testing and coveralls
            [jonase/eastwood "0.2.4"] ; Linter
            [lein-bikeshed "0.4.1"] ; Another linter
            [venantius/ultra "0.5.1"] ; Colorized test output
            [lein-nvd "0.6.0"]
            [lein-kibit "0.1.6"]
            [lein-ancient "0.6.15"]
            ]

  :profiles { :test {:resource-paths ["resources/test"]}
              :dev
                    {:resource-paths ["resources/dev"]
                     :dependencies [[javax.servlet/servlet-api "2.5"]
                                    [ring/ring-mock "0.3.1"]
                                    ]
                     :ring {:port 4000}}
              :uberjar {:ring {:port 4000}}}

  :ring { :handler clojure-rest.handler/app}

  :eastwood {:exclude-linters [:constant-test]
             :include-linters [:deprecations]
             :exclude-namespaces [clojure-rest.config.routes]
             ;:debug [:all]
             ;:out "eastwood-warnings.txt"
             }
  :bikeshed {:max-line-length 100}

  :aliases {"migrate"  ["run" "-m" "clojure-rest.config.database/ragtime-migrate"]
            "rollback" ["run" "-m" "clojure-rest.config.database/ragtime-rollback"]})
