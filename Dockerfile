FROM maven:3.8.7-openjdk-17

# Chrome ve ChromeDriver kurulumları
RUN apt-get update && apt-get install -y wget unzip xvfb libxi6 libgconf-2-4

# Chrome kurulum
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list'
RUN apt-get update && apt-get install -y google-chrome-stable

# ChromeDriver versiyonunu Chrome ile uyumlu seç
ARG CHROMEDRIVER_VERSION=116.0.5845.96
RUN wget -q https://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_linux64.zip
RUN unzip chromedriver_linux64.zip
RUN mv chromedriver /usr/local/bin/
RUN chmod +x /usr/local/bin/chromedriver

# Display (headless için) ortamı ayarla
ENV DISPLAY=:99

WORKDIR /app
COPY . /app

CMD ["mvn", "clean", "verify"]