def sendTelegramNotification(status) {
    withCredentials([string(credentialsId: 'telegram_bot_token', variable: 'BOT_TOKEN')]) {
        def startTime = env.START_TIME ?: 'null'
        def endTime = env.END_TIME ?: 'null'

        sh """
          curl -s -X POST https://api.telegram.org/bot${BOT_TOKEN}/sendMessage \
               -d chat_id=-4286525343 \
               -d parse_mode=MarkdownV2 \
               -d text="*Performance Result*\n\n\
\\- *Job Name:* ${env.JOB_NAME}\n\
\\- *Status:* ${status}\n\
\\- *Build Number:* ${env.BUILD_NUMBER}\n\
\\- *Start Time:* ${startTime}\n\
\\- *End Time:* ${endTime}" \
        """
    }
}

def sendTelegramDocument() {
    withCredentials([string(credentialsId: 'telegram_bot_token', variable: 'BOT_TOKEN')]) {
        sh """
          curl -v -X POST https://api.telegram.org/bot${BOT_TOKEN}/sendDocument \
               -F chat_id=-4286525343 \
               -F document=@example.tar
        """
    }
}
