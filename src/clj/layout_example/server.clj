(ns layout-example.server
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :as resources]
            [ring.util.response :as response]
            [hiccup.core :as hiccup]
            [hiccup.page :as page])
  (:gen-class))

(defn render-app []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (page/html5
          [:html
           [:head
            (page/include-js "js/jquery-2.1.1.min.js")
            (page/include-js "js/cljs.js")
            [:script#lt_ws {:src "http://localhost:45045/socket.io/lighttable/ws.js"}]
            (page/include-css "css/page.css")]
           [:body
            [:div#root.container.horizontal
             [:div#leftbar.container.vertical.resizable "Left Bar"]
             [:div#main.container.vertical
              [:div#content.container.tabbed "Tab Container"]
              [:div#bottombar.container.resizable "Bottom Bar"]]
             [:div#rightbar.container.vertical.resizable "Right Bar"]
             ]
            ]])
   })

(defn handler [request]
      (render-app))

(def app
  (-> handler
    (resources/wrap-resource "public")))

(defn -main [& args]
  (jetty/run-jetty app {:port 3000}))

