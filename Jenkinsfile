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
                    git branch: 'bacem',
                        url: 'https://github.com/Devops-5Arctic/Backend-Pi',
                        credentialsId: 'github-credentials'
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
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
