(ns clojure-rest.config.routes
  (:use clojure-rest.controllers.document-controller)
  (:require [compojure.core :refer :all]
          [compojure.route :as route]))

(defroutes app-routes
      (context "/documents" [] (defroutes documents-routes)
        (GET  "/" [] (get-all-documents)))
      (route/not-found "Not Found"))

      ; (defroutes app-routes
      ;       (context "/documents" [] (defroutes documents-routes)
      ;         (GET  "/" [] (get-all-documents))
      ;         (POST "/" {body :body} (create-new-document body))
      ;         (context "/:id" [id] (defroutes document-routes)
      ;           (GET    "/" [] (get-document id))
      ;           (PUT    "/" {body :body} (update-document id body))
      ;           (DELETE "/" [] (delete-document id))))
      ;       (route/not-found "Not Found"))
