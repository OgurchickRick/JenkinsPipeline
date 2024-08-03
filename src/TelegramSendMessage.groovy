withCredentials([string(credentialsId: 'telegram_bot_token', variable: 'BOT_TOKEN')]) {
    sh """
          curl -s -X POST https://api.telegram.org/bot${BOT_TOKEN}/sendMessage \
               -d chat_id=-4286525343 \
               -d parse_mode=MarkdownV2 \
               -d text="*Performance Result*\n\n\
\\- *Job Name:* ${env.JOB_NAME}\n\
\\- *Status:* \n\
\\- *Build Number:* ${env.BUILD_NUMBER}" \
        """
}
