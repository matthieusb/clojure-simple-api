(ns clojure-rest.integration.controllers.document-controller-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]
            [clojure.java.jdbc :as jdbc]
            [clojure-rest.handler :refer :all]
            [clojure.tools.logging :as log]
            [clojure-rest.config.database :as database]))

(def expected-headers {"Content-Length" "119", "Content-Type" "application/json; charset=utf-8"})
(def initial-document-list-expected (json/generate-string [{:id_document "1" :title "title1" :description "text1"} {:id_document "2" :title "title2" :description "text2"}]))

; ------------------
; -- Tests on get
; ------------------
(deftest test-document-controller-list
  (database/reinit-database)

  (testing "Testing document list route worked"
    (let [response (app (mock/request :get "/documents"))]
      (is (= (:status response) 200))
      (is (= (:headers response) expected-headers))
      (is (= (slurp (:body response)) initial-document-list-expected)))))

(deftest test-document-controller-getbyid
  (database/reinit-database)

  (testing "Testing get document by id route worked"
    (let [response (app (mock/request :get "/documents/1"))]
      (is (= (:status response) 200))))

  (testing "Testing get document by id route failed with incorrect id"
    (let [response (app (mock/request :get "/documents/notfound"))]
      (is (= (:status response) 404)))))

; ------------------
; -- Tests on create
; ------------------

(def document-to-create {:title "newDocumentTitle" :description "newDocumentText"})

(deftest test-document-controller-create
  (database/reinit-database)

  (testing "Testing document creation route worked"
    (let [request (mock/request :post "/documents" (json/generate-string document-to-create))]
    (let [response (app (mock/content-type request "application/json"))]
      (let [response-body (slurp (:body response))]
        (is (= (:status response) 200))
        (is (= (get (json/parse-string response-body) "title") (:title document-to-create)))
        (is (= (get (json/parse-string response-body) "description") (:description document-to-create)))
        (is (= (count (jdbc/query database/db-h2-connection ["select * from \"document\""])) 3))))))

  (testing "Testing document creation route failed because of incorrect document"
    (let [request (mock/request :post "/documents" (json/generate-string {:id_document "dummy" :title "newDocumentTitle" :description "newDocumentText" :wrongattribute "badvalue"}))]
    (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 400))))))

; ------------------
; -- Tests on update
; ------------------
(def document-to-update {:title "titre1Updated" :description "texte1Updated"})

(deftest test-document-controller-update
  (database/reinit-database)

  (testing "Testing document update route worked"
    (let [request (mock/request :put "/documents/1" (json/generate-string document-to-update))]
      (let [response (app (mock/content-type request "application/json"))]
      (let [response-body (slurp (:body response))]
        (is (= (:status response) 200))
        (is (= (get (json/parse-string response-body) "title") (:title document-to-update)))
        (is (= (get (json/parse-string response-body) "description") (:description document-to-update)))))))

  (testing "Testing document update route failed with incorrect id"
    (let [request (mock/request :put "/documents/notfound" (json/generate-string {:title "wontwork" :description "causeIncorrectid"}))]
      (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 404)))))

  (testing "Testing document update route failed with incorrect document format"
    (let [request (mock/request :put "/documents/invalidformat" (json/generate-string {:id_document "incorrectformat" :title "wontwork"}))]
      (let [response (app (mock/content-type request "application/json"))]
      (is (= (:status response) 400))))))

; ------------------
; -- Tests on delete
; ------------------
(deftest test-document-controller-delete
  (database/reinit-database)
  (testing "Testing document delete route worked"
    (let [response (app (mock/request :delete "/documents/1"))]
      (is (= (:status response) 204))
      (is (= (count (jdbc/query database/db-h2-connection ["select * from \"document\""])) 1))))

  (database/reinit-database)
  (testing "Testing document delete route failed"
    (let [response (app (mock/request :delete "/documents/notfound"))]
      (is (= (:status response) 404)))))
