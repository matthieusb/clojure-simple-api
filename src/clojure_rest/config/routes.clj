(ns clojure-rest.config.routes
  (:require [compojure.core :refer :all]
            [clojure-rest.controllers.document-controller :as document-controller]
            [compojure.route :as route]))

(defroutes app-routes
      (context "/documents" [] (defroutes documents-routes
        (GET  "/" [] (document-controller/get-all-documents))
        (POST "/" {body :body} (document-controller/create-new-document body))
        (context "/:id" [id] (defroutes documents-route
          (GET    "/" [] (document-controller/get-document id))
          (PUT    "/" {body :body} (document-controller/update-document id body))
          (DELETE "/" [] (document-controller/delete-document id))))))
      (route/not-found "Not Found"))
