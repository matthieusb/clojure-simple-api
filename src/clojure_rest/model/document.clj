(ns clojure-rest.model.document
  (:use [korma.core :only [defentity table entity-fields pk]])
  (:require [clojure.tools.logging :as log]
            [schema.core :as schema]))

; -- Korma configuration
(defentity document
  (table :document) ; Associated table
  (pk :id_document) ; primary key
  (entity-fields :id_document :title :description)) ; Default field for select

; -- Validation schema for complete document
(def document-schema
  {:id_document schema/Str
   :title schema/Str
   :description schema/Str})

; -- Validation for input rest document
(def document-schema-rest-in
 {:title schema/Str
  :description schema/Str})
