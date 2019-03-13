# clojure-simple-api

[![Build Status](https://travis-ci.org/matthieusb/clojure-simple-api.svg?branch=master)](https://travis-ci.org/matthieusb/clojure-simple-api)
[![Coverage Status](https://coveralls.io/repos/github/matthieusb/clojure-simple-api/badge.svg?branch=master)](https://coveralls.io/github/matthieusb/clojure-simple-api?branch=master)
[![Dependencies Status](https://jarkeeper.com/matthieusb/clojure-simple-api/status.svg)](https://jarkeeper.com/matthieusb/clojure-simple-api)

This application is a simple demonstration api used for an ippon-article about Clojure.
It demonstrates usage of compojure, ring, cheshire, mock, schema ...

## Prerequisites

You will need [Leiningen](https://github.com/technomancy/leiningen) 2.0.0 or above installed. See the dependencies status above for more info on libraries status.

## Testing
To launch tests for this application, run :

```
lein test
```

To test coverage, the project uses the [Cloverage plugin](https://github.com/cloverage/cloverage), just launch :
```
lein cloverage
```

If you want to launch the linter, use the following command :
```
lein eastwood
```

## Running

Before running the server for the first time, don't forget to run the following command :

```
lein migrate
```

To start a web server for the application, run:

```
lein ring server
```

Then, to access the swagger ui, go to `http://localhost:8080/api-docs/`

The application resorts to an h2 database for both testing and running, since it is just a demonstration.
You don't need to start a database or anything, everything is stored temporarily in the *resources*/**env**/*db* folder.

## Packaging
To package this application for production, use the following command :

```
lein ring uberjar
```

**However** do not forget to :
  - Add production paths for resources to project.clj
  - Add a production folder to the project in resources, containing corresponding configuration files

## Quick look

![](swagger_screenshot.png?raw=true)

## License

Apache 2.0 License
