 steps {
     script {
         // Установить время начала сборки
         env.START_TIME = new Date().format("yyyy-MM-dd HH:mm:ss")
     }
     echo 'Building project...'
     writeFile file: 'example.txt', text: 'This is an example file.'
     sh 'tar -cvf example.tar example.txt'
 }
