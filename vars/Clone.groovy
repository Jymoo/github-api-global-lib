def call() {
        echo "Cloning repository..."
        dir('lendy') {
            git branch: 'main', url: 'https://github.com/mosesmbadi/lendy.git'
            echo "clonned successfull"
        }
}
