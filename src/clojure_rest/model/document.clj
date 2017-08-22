(ns clojure-rest.model.document
  (:require [schema.core :as schema]))

(def Document-schema
  {:id_document schema/Str
   :title schema/Str
   :text schema/Str})

(defrecord Document [id_document title text])

(defn document
  "Creates a new document"
  [{:keys [id_document title text]}]
  (-> Document id_document title text))

(defn validate-document-map
  "Creates a new document from a map, with validation"
  [mapInput]
  (schema/validate Document-schema mapInput))
