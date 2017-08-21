(ns clojure-rest.integration.controllers.document-controller-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure-rest.handler :refer :all]
            [clojure-rest.config.database :as database]))

(deftest test-document-controller
  (database/initDatabase)
  (testing "Testing document list route"
    (let [response (app (mock/request :get "/documents"))]
      (is (= (:status response) 200)))))
      ;(is (= (:body response) "Not found"))))
