Forked from https://github.com/WorldWindLabs/SpaceBirds

Web application to display space debris heatmap and more.

Deployment:

You will need to have a web browser (I've used Chrome), Node.js and npm installed

1) Go to SpaceBirds\Web application
2) Install connect via "npm -i connect"
3) Install serve-static via "npm -i serve-static"
4) Run "node server.js"
5) Go to http://localhost:8080/index.html#
6) To make API work, lauch chrome with --disable-web-security param to avoid issue with CORS
example: C:\Users\MyUser\Desktop\chrome-win\chrome.exe --disable-web-security --disable-gpu --user-data-dir=~/chromeTemp.