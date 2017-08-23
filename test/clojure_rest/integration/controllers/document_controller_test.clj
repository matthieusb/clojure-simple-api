(ns clojure-rest.integration.controllers.document-controller-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]
            [clojure.java.jdbc :as jdbc]
            [clojure-rest.handler :refer :all]
            [clojure-rest.config.database :as database]))

(def expectedHeaders {"Content-Type" "application/json; charset=utf-8"})
(def initialDocumentList (json/generate-string [{:id_document "1" :title "titre1" :text "text1"} {:id_document "2" :title "titre2" :text "text2"}]))

(deftest test-document-controller-list
  (database/initDatabase)

  (testing "Testing document list route worked"
    (let [response (app (mock/request :get "/documents"))]
      (is (= (:status response) 200))
      (is (= (:headers response) expectedHeaders))
      (is (= (:body response) initialDocumentList)))))

(deftest test-document-controller-getbyid
  (database/initDatabase)
  (testing "Testing get document by id route worked"
    (let [response (app (mock/request :get "/documents/1"))]
      (is (= (:status response) 200))))

  (testing "Testing get document by id route failed with incorrect id"
    (let [response (app (mock/request :get "/documents/notfound"))]
      (is (= (:status response) 404)))))

(deftest test-document-controller-create
  (database/initDatabase)

  (testing "Testing document creation route worked"
    (let [request (mock/request :post "/documents" (json/generate-string {:id_document "dummy" :title "newDocumentTitle" :text "newDocumentText"}))]
    (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 200)))))
  ; TODO Add body test and count test

  (testing "Testing document creation route failed because of incorrect document"
    (let [request (mock/request :post "/documents" (json/generate-string {:id_document "dummy" :title "newDocumentTitle" :text "newDocumentText" :wrongattribute "badvalue"}))]
    (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 400)))))) ; FIXME This test causes an error

(deftest test-document-controller-update
  (database/initDatabase)

  (testing "Testing document update route worked"
    (let [request (mock/request :put "/documents/1" (json/generate-string {:id_document "1" :title "titre1Updated" :text "texte1Updated"}))]
      (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 200)))))
  ; TODO Add tests on body returned and database

  (testing "Testing document update route failed with incorrect id"
    (let [request (mock/request :put "/documents/notfound" (json/generate-string {:id_document "noid" :title "wontwork" :text "causeIncorrectid"}))]
      (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 404)))))
  ; TODO Add tests on body returned and database

  (testing "Testing document update route failed with incorrect document format"
    (let [request (mock/request :put "/documents/invalidformat" (json/generate-string {:id_document "incorrectformat" :title "wontwork"}))]
      (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 400)))))
  ; TODO Add tests on body returned and database
  )

(deftest test-document-controller-delete
  (database/initDatabase)

  (testing "Testing document delete route worked"
    (let [response (app (mock/request :delete "/documents/1"))]
      (is (= (:status response) 204))))
  ; TODO Add tests on database

  (testing "Testing document delete route failed"
    (let [response (app (mock/request :delete "/documents/notfound"))]
      (is (= (:status response) 404))))) ; FIXME This test fails
; TODO Add tests on database and correct error
