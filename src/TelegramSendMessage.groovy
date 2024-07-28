pipeline {
    agent any
    environment {
        START_TIME = ''
        END_TIME = ''
    }
    stages {
        stage('Checkout') {
            steps {
                echo 'Checkout..'
            }
        }
        stage('Build') {
            steps {
                script {
                    // Установить время начала сборки
                    env.START_TIME = new Date().format("yyyy-MM-dd HH:mm:ss")
                }
                echo 'Building project...'
                writeFile file: 'example.txt', text: 'This is an example file.'
                sh 'tar -cvf example.tar example.txt'
            }
        }
        stage('Publish') {
            steps {
                echo 'Deploying...'
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
            withCredentials([string(credentialsId: 'telegram_bot_token', variable: 'BOT_TOKEN')]) {
                // Отправить текстовое сообщение
                sh """
                    curl -s -X POST https://api.telegram.org/bot${BOT_TOKEN}/sendMessage \
                         -d chat_id=-4286525343 \
                         -d parse_mode=MarkdownV2 \
                         -d text="*Performance Result*\n\n\
\\- *Job Name:* ${env.JOB_NAME}\n\
\\- *Status:* ✅ Success\n\
\\- *Build Number:* ${env.BUILD_NUMBER}\n\
\\- *Start Time:* ${env.START_TIME}\n\
\\- *End Time:* ${env.END_TIME}" \
                """

                // Отправить файл
                sh """
                    curl -s -X POST https://api.telegram.org/bot${BOT_TOKEN}/sendDocument \
                         -F chat_id=-4286525343 \
                         -F document=@example.tar \
                """
            }
        }

        failure {
            withCredentials([string(credentialsId: 'telegram_bot_token', variable: 'BOT_TOKEN')]) {
                // Отправить текстовое сообщение
                sh """
                    curl -s -X POST https://api.telegram.org/bot${BOT_TOKEN}/sendMessage \
                         -d chat_id=-4286525343 \
                         -d parse_mode=MarkdownV2 \
                         -d text="*Performance Result*\n\n\
\\- *Job Name:* ${env.JOB_NAME}\n\
\\- *Status:* ❌ Failed\n\
\\- *Build Number:* ${env.BUILD_NUMBER}\n\
\\- *Start Time:* ${env.START_TIME}\n\
\\- *End Time:* ${env.END_TIME}" \
                """

                // Отправить файл
                sh """
                    curl -s -X POST https://api.telegram.org/bot${BOT_TOKEN}/sendDocument \
                         -F chat_id=-4286525343 \
                         -F document=@example.tar \
                """
            }
        }
    }
}
