FROM openjdk:11-jdk

# Установка Android SDK
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    && wget -q https://dl.google.com/android/repository/commandlinetools-linux-6200805_latest.zip \
    -O /tmp/cmdline-tools.zip \
    && mkdir -p /sdk/cmdline-tools \
    && unzip -q /tmp/cmdline-tools.zip -d /sdk/cmdline-tools \
    && rm /tmp/cmdline-tools.zip

ENV ANDROID_HOME /sdk
ENV PATH $ANDROID_HOME/cmdline-tools/latest/bin:$PATH

# Установка других зависимостей (Gradle, и т.д.)
RUN apt-get install -y gradle

# Копирование проекта
COPY . /app
WORKDIR /app

# Сборка APK
RUN ./gradlew assembleDebug