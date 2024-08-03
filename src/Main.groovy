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
    success {
      script {
        load 'src/TelegramSendMessage.groovy'
      }
    }
    failure {
      script {
        load 'src/TelegramSendMessage.groovy'
      }
    }
  }
}
