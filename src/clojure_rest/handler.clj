(ns clojure-rest.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as ring-json]
            [clojure-rest.config.routes :as config-routes]))


; ------------------- APP CONFIG

(def app
  (-> config-routes/app-routes
      (ring-json/wrap-json-body  {:keywords? true :bigdecimals? true})
      (ring-json/wrap-json-response)))

      ; (rs/swagger-json {:swagger "2.0",
      ;  :info {:title "Swagger API", :version "0.0.1"},
      ;  :produces ["application/json"],
      ;  :consumes ["application/json"],
      ;  :paths {},
      ;  :definitions {}})
