pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven-3.6.3'
    }

    stages {

        stage('Checkout') {
            steps {
                script {
                    // Checkout the Backend-Pi repository with credentials
                    git branch: 'bacem',
                        url: 'https://github.com/Devops-5Arctic/Backend-Pi',
                        credentialsId: 'github-credentials'
                }
            }
        }

        stage('Build') {
            steps {
                // Clean and install the application
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                // Run unit tests
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                // Package the application (optional)
                sh 'mvn package'
            }
        }

        stage('Deploy') {
            steps {
                // Placeholder for deployment steps
                echo 'Deploying the project...'
                // You can add actual deployment commands here
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Build was successful.'
        }
        failure {
            echo 'Build failed.'
        }
    }
}