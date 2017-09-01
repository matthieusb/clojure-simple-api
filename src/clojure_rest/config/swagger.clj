(ns clojure-rest.config.swagger
  (:require [ring.swagger.swagger2 :as rs]))

(def swagger-routes
  (rs/swagger-json {
                    :info {:version "1.0.0"
                           :title "Simple Clojure Rest Api"
                           :description "Rest api to demonstrate clojure ecosystemp capabilities"}
                    :paths { "/documents" {:get {}} }
                    }))
