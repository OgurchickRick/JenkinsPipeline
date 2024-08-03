def telegramUtils

pipeline {
  agent any
  environment {
    START_TIME = ''
    END_TIME = ''
  }
  stages {
    stage('Initialize') {
      steps {
        script {
          // Загрузить функции из внешнего скрипта
          telegramUtils = load 'src/TelegramUtils.groovy'
        }
      }
    }
    stage('Checkout') {
      steps {
        script {
          load 'src/checkout.groovy'
        }
      }
    }
    stage('Build') {
      steps {
        script {
          load 'src/build.groovy'
        }
      }
    }
    stage('Publish') {
      steps {
        script {
          load 'src/publish.groovy'
        }
      }
    }
  }
  post {
    success {
      script {
        telegramUtils.sendTelegramNotification('✅ Success')
      }
    }
    failure {
      script {
        telegramUtils.sendTelegramNotification('❌ Failed')
      }
    }
  }
}
