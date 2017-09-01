(ns clojure-rest.config.routes
  (:require [compojure.core :refer :all]
            [clojure-rest.config.swagger :as swagger-conf]
            [clojure-rest.controllers.document-controller :as document-controller]
            [ring.swagger.swagger2 :as rs]
            [compojure.route :as route]))


(def document-routes
  (context "/documents" []
    (GET  "/" [] (document-controller/get-all-documents))
    (POST "/" {body :body} (document-controller/create-new-document body))
    (context "/:id" [id]
      (GET    "/" [] (document-controller/get-document id))
      (PUT    "/" {body :body} (document-controller/update-document id body))
      (DELETE "/" [] (document-controller/delete-document id)))))



(defroutes app-routes
  document-routes
  swagger-conf/swagger-routes
  (route/not-found "Not Found"))
