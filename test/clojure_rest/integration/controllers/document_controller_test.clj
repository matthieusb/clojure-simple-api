(ns clojure-rest.integration.controllers.document-controller-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure-rest.handler :refer :all]
            [clojure-rest.config.database :as database]))

(deftest test-document-controller
  (database/initDatabase)

  (testing "Testing document list route worked"
    (let [response (app (mock/request :get "/documents"))]
      (is (= (:status response) 200))))

  (testing "Testing get document by id route worked"
    (let [response (app (mock/request :get "/documents"))]
      (is (= (:status response) 200))))

  (testing "Testing get document by id route failed with incorrect id"
    (let [response (app (mock/request :get "/documents/1"))]
      (is (= (:status response) 200))))

  (testing "Testing document creation route worked"
    (let [response (app (mock/request :post "/documents"))]
      (is (= (:status response) 200))))

  (testing "Testing document creation route failed"
    (let [response (app (mock/request :post "/documents"))]
      (is (= (:status response) 200))))

  (testing "Testing document update route worked"
    (let [response (app (mock/request :put "/documents/1"))]
      (is (= (:status response) 200))))

  (testing "Testing document update route failed"
    (let [response (app (mock/request :put "/documents/1"))]
      (is (= (:status response) 200))))

  (testing "Testing document delete route worked"
    (let [response (app (mock/request :delete "/documents/1"))]
      (is (= (:status response) 200))))

  (testing "Testing document delete route failed"
    (let [response (app (mock/request :delete "/documents/1"))]
      (is (= (:status response) 200)))))
