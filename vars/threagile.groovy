// Define the function with a `call` method
def call(Map params = [:]) {
    // Default parameters
    def dockerImage = params.dockerImage ?: 'threagile/threagile:latest'
    def threagileYamlPath = params.threagileYamlPath ?: 'threagile.yaml'
    def outputDir = params.outputDir ?: 'results'
    def reportFile = params.reportFile ?: 'report.pdf'



     // Check if Docker is installed
    // def dockerInstalled = false
    // try {
    //     sh(script: "docker --version", returnStatus: true)
    //     dockerInstalled = true
    // } catch (Exception e) {
    //     echo "Docker is not installed. Installing Docker..."
    // }
    
    // // Install Docker if it's not installed
    // if (!dockerInstalled) {
    //     // For Ubuntu/Debian-based systems
    //     sh '''
    //         sudo apt-get update
    //         sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common
    //         curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
    //         echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    //         sudo apt-get update
    //         sudo apt-get install -y docker-ce docker-ce-cli containerd.io
    //     '''
    //     // Start Docker service
    //     sh 'sudo systemctl enable docker'
    //     sh 'sudo systemctl start docker'
    // }





    
    
    // Check if the Docker image exists locally
    def imageExists = false
    try {
        // Try to get the image info to check if it exists
        sh(script: "docker image inspect ${dockerImage}", returnStatus: true)
        imageExists = true
    } catch (Exception e) {
        // If the image doesn't exist, it will throw an error, and we'll pull the image
        echo "Docker image not found locally, pulling..."
    }
    
    // Pull the image if it doesn't exist locally
    if (!imageExists) {
        sh "docker pull ${dockerImage}"
    }

    // Run the Docker container with the provided command    docker run --rm -v \$(pwd):/app/work ${dockerImage} -verbose -model /app/work/${threagileYamlPath} -output /app/work/${outputDir}           docker run --rm -v /tmp:/app/work ${dockerImage} -verbose -model /app/work/${threagileYamlPath} -output /app/work/${outputDir}
    echo "Running Docker container..."
    sh """
        docker run --rm -v /tmp:/app/work ${dockerImage} -verbose -model /app/work/${threagileYamlPath} -output /app/work/${outputDir}
    """
    
    // After running the container, check if the report.pdf file exists in the output directory
    def reportPath = "${PWD}/${outputDir}/${reportFile}"
    if (fileExists(reportPath)) {
        // Create a clickable link to the report in the Jenkins log
        echo "The report is ready. Click the link below to view the report:"
        echo "<a href='file://${reportPath}'>Click here to view the ${reportFile}</a>"
    } else {
        echo "No ${reportFile} found at ${reportPath}"
    }
}
