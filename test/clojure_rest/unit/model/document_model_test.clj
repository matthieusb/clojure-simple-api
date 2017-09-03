(ns clojure-rest.unit.model.document-model-test
  (:require [clojure.test :refer :all]
            [schema.core :as schema]
            [clojure-rest.model.document :as document]))

(def valid-document-map {:id_document "1" :title "title1" :description "text1"})

(def wrong-document-map-not-enough-keywords {:id_document "1" :title "title1"})
(def wrong-document-map-too-many-keywords
  {:id_document "1" :title "title1" :description "text1" :oneMoreKeyword "wrong"})
(def wrong-document-map-invalid-types {:id_document "1" :title 100 :description 1})

(deftest test-document-validation-valid
  (testing "Testing document validation worked"
  (let [validation-res (schema/validate document/document-schema valid-document-map)]
    (is (= validation-res valid-document-map)))))

(deftest test-document-validation-invalid
  (testing "Testing document validation failed with not enough keywords in map"
    (is (thrown?  Exception (schema/validate document/document-schema
                                             wrong-document-map-not-enough-keywords))))

  (testing "Testing document validation failed with too many keywords in map"
    (is (thrown? Exception (schema/validate document/document-schema
                                            wrong-document-map-too-many-keywords))))

  (testing "Testing document validation failed with invalid types in map"
    (is (thrown? Exception (schema/validate document/document-schema
                                            wrong-document-map-invalid-types)))))

(def valid-document-rest-in-map {:title "title1" :description "text1"})

(def wrong-document-rest-in-map-not-enough-keywords {:id_document "1" :title "title1"})
(def wrong-document-rest-in-map-too-many-keywords
  {:id_document "1" :title "title1" :description "text1" :oneMoreKeyword "wrong"})
(def wrong-document-rest-in-map-invalid-types {:title 100 :description 1})

(deftest test-document-rest-in-validation-valid
  (testing "Testing document rest input validation worked"
  (let [validation-res (schema/validate document/document-schema-rest-in
                                        valid-document-rest-in-map)]
    (is (= validation-res valid-document-rest-in-map)))))

(deftest test-document-rest-in-validation-invalid
  (testing "Testing document rest input validation failed with not enough keywords in map"
    (is (thrown? Exception (schema/validate document/document-schema-rest-in
                          wrong-document-rest-in-map-not-enough-keywords)))))

  (testing "Testing document rest input validation failed with too many keywords in map"
    (is (thrown? Exception (schema/validate document/document-schema-rest-in
                                            wrong-document-rest-in-map-too-many-keywords))))

  (testing "Testing document rest input validation failed with invalid types in map"
    (is (thrown? Exception (schema/validate document/document-schema-rest-in
                                            wrong-document-rest-in-map-invalid-types))))
