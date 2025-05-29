def call() {
        echo "Pushing Docker image to Docker Hub..."
        dir('lendy') {
            def dockerImageTag = "${env.DOCKER_HUB_REPO}:latest"
            withCredentials([usernamePassword(
                credentialsId: 'dockerhub-creds',
                usernameVariable: 'DOCKER_USER',
                passwordVariable: 'DOCKER_PASS'
            )]) {
                sh """
                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                    docker tag $IMAGE_NAME $dockerImageTag
                    docker push $dockerImageTag
                """
            }
        }
}