def call() {
    echo "Running Docker image..."
    sh '''
        docker rm -f lendy_container || true
        docker run -d --name lendy_container -p 8080:8080 $IMAGE_NAME
    '''
}
