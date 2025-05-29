def call() {
        echo "Building Docker image..."
        dir('lendy') {
            sh 'docker build -t $IMAGE_NAME .'
        }
}

