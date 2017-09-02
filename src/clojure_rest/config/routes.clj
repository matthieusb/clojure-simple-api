(ns clojure-rest.config.routes
  (:require [compojure.api.sweet :refer :all]
            [compojure.api.middleware :refer :all]
            [ring.util.http-response :refer :all]
            [clojure-rest.config.swagger :as swagger-conf]
            [clojure-rest.controllers.document-controller :as document-controller]
            [clojure-rest.model.document :as document-model]
            [compojure.route :as route]))

(def document-routes
  (context "/documents" []
    (GET  "/" []
          :summary "Gets all available document"
          (document-controller/get-all-documents))
    (GET    "/:id" []
            :path-params [id :- String]
            :summary "Gets a specific document by id"
            (document-controller/get-document id))
    (POST "/" []
          :body [document document-model/document-schema]
          :summary "Creates new document"
          (document-controller/create-new-document document))
    (PUT    "/:id" []
            :path-params [id :- String]
            :body [document document-model/document-schema]
            :summary "Updates and existing document by id"
            (document-controller/update-document id document))
    (DELETE "/:id" []
            :path-params [id :- String]
            :summary "Updates and existing document by id"
            (document-controller/delete-document id))))

(defapi app-routes
   swagger-conf/swagger-routes
   document-routes
  (route/not-found "Not Found"))
