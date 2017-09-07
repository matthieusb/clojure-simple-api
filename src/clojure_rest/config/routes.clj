(ns clojure-rest.config.routes
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [clojure-rest.config.swagger :as swagger-conf]
            [clojure-rest.controllers.document-controller :as document-controller]
            [clojure-rest.model.document :as document-model]))

(def document-routes
  (context "/documents" [] :tags ["api-documents"]
    (GET "/" []
          :summary "Gets all available document"
          :return [document-model/document-schema]
          :responses {200 {:schema [document-model/document-schema],
                           :description "List of documents"}}
          (document-controller/get-all-documents))
    (GET "/:id" []
            :path-params [id :- String]
            :return document-model/document-schema
            :responses {200 {:schema document-model/document-schema,
                             :description "The document found"}
                        404 {:description "No document found for this id"}}
            :summary "Gets a specific document by id"
            (document-controller/get-document id))
    (POST "/" []
          :body [document document-model/document-schema-rest-in]
          :return document-model/document-schema
          :responses {201 {:schema document-model/document-schema,
                           :description "Returns the created document"}
                      400 {:description "Malformed request body"}}
          :summary "Creates new document"
          (document-controller/create-new-document document))
    (PUT "/:id" []
            :path-params [id :- String]
            :body [document document-model/document-schema-rest-in]
            :return document-model/document-schema
            :responses {200 {:schema document-model/document-schema,
                             :description "The updated document"}
                        400 {:description "Malformed request body"}
                        404 {:description "No document found for this id"}}
            :summary "Updates and existing document by id"
            (document-controller/update-document id document))
    (DELETE "/:id" []
            :path-params [id :- String]
            :responses {204 {:description "Document successfuly deleted"}
                        404 {:description "No document found for this id"}}
            :summary "Updates and existing document by id"
            (document-controller/delete-document id))))

(defapi app-routes
   swagger-conf/swagger-routes
   document-routes
  (undocumented (compojure.route/not-found (ok {:not "found"}))))
