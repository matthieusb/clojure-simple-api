(ns clojure-rest.config.swagger)

(def swagger-routes
  {:swagger
   {:ui "/api-docs"
    :spec "/swagger.json"
    :data {:info {:title "Simple Clojure API"
                  :description "Example Clojure api for Ippon Blog Article"}
           :tags [{:name "api", :description "Api endpoints"}]}}})
