def call() {
    pipeline {
        agent any

        environment {
            IMAGE_NAME     = "lendy-app"
            SECRET_KEY     = credentials('DJANGO_SECRET_KEY')
            COMPOSE_FILE   = "lendy/docker-compose.yml"
            DOCKER_HUB_REPO = 'jymo/lendy-app'
        }

        stages {

            stage('check env'){
                steps{
                    CheckEnv()
                }
                
            }
            
            stage('Clone') {
                steps {
                    script {
                        Clone()
                    }
                }
            }

            stage('Install Dependencies & Test') {
                steps {
                    script {
                        InstallAndTest()
                    }
                }
            }

            stage('Build Docker Image') {
                steps {
                    script {
                        BuildDocker()
                    }
                }
            }

            stage('Push Docker Image to dockerhub') {
                steps {
                    script {
                        PushDocker()
                    }
                }
            }

            stage('Run Docker Image') {
                steps {
                    script {
                        RunDocker()
                    }
                }
            }
        }

        post {
            success {
                echo "Build and deploy successful!"
            }
            failure {
                echo "Build or deploy failed."
            }
            always {
                echo "Pipeline finished."
            }
        }
    }
}
