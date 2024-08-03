def sendTelegramNotification(message) {
    // Реализация отправки сообщения в Telegram
    def token = 'YOUR_BOT_TOKEN'
    def chatId = 'YOUR_CHAT_ID'
    def url = "https://api.telegram.org/bot${token}/sendMessage"
    def payload = [
            chat_id: chatId,
            text: message
    ]
    def response = httpRequest(
            url: url,
            httpMode: 'POST',
            requestBody: new groovy.json.JsonBuilder(payload).toPrettyString(),
            contentType: 'APPLICATION_JSON'
    )
    echo "Telegram response: ${response}"
}

def sendTelegramDocument() {
    // Реализация отправки документа в Telegram
    def token = 'YOUR_BOT_TOKEN'
    def chatId = 'YOUR_CHAT_ID'
    def url = "https://api.telegram.org/bot${token}/sendDocument"
    def payload = [
            chat_id: chatId
            // Здесь добавьте необходимые параметры для отправки документа
    ]
    def response = httpRequest(
            url: url,
            httpMode: 'POST',
            requestBody: new groovy.json.JsonBuilder(payload).toPrettyString(),
            contentType: 'APPLICATION_JSON'
    )
    echo "Telegram response: ${response}"
}
