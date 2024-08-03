pipeline {
  agent any
  environment {
    START_TIME = ''
    END_TIME = ''
  }
  stages {
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
    always {
      script {
        // Установить время окончания сборки
        env.END_TIME = new Date().format("yyyy-MM-dd HH:mm:ss")
      }
    }
    success {
      script {
        load 'src/TelegramSendMessage.groovy'
        sendTelegramNotification('✅ Success')
        sendTelegramDocument()
      }
    }
    failure {
      script {
        load 'src/TelegramSendMessage.groovy'
        sendTelegramNotification('❌ Failed')
        sendTelegramDocument()
      }
    }
  }
}
