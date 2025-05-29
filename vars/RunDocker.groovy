def call() {
    echo "Running Docker image..."
    sh '''
        echo "Freeing port 7000 if it's in use..."
        container_id=$(docker ps -q --filter "publish=7000")
        if [ -n "$container_id" ]; then
            echo "Killing container using port 7000..."
            docker rm -f $container_id
        fi
        docker rm -f lendy_container || true
        docker run -d --name lendy_container -p 7000:8080 $IMAGE_NAME
    '''
}
