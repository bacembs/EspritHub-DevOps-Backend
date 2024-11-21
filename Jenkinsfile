pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven-3.6.3'
    }

    environment {
        DOCKER_HUB_REPO = 'bacem12/bacembensoltana-5arctic3-g1-esprithub'.toLowerCase()
        IMAGE_NAME = 'bacembensoltana-5arctic3-g1-esprithub'.toLowerCase()
        DOCKER_CREDENTIALS_ID = 'dockerhub-credentials'
        IMAGE_TAG = 'bacembensoltana-5arctic3-g1-esprithub-backend'
        SONARQUBE_SERVER = 'SonarQube'
        SONAR_PROJECT_KEY = 'esprithub'
        SONAR_PROJECT_NAME = 'esprithub'
        SONAR_PROJECT_VERSION = '1.0'
        SONAR_LOGIN_TOKEN = credentials('sonarqube-cred')
        NEXUS_URL = 'http://localhost:8081/repository/maven-releases-esprithub/'
        NEXUS_CREDENTIALS_ID = 'nexus-credentials'
        DEPLOYMENT_PATH = '/home/bacembensoltana/terraform-aks'

    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    git branch: 'main',
                        url: 'https://github.com/Devops-5Arctic/5ARCTIC3-G1-EspritHub',
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

        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv(SONARQUBE_SERVER) {
                        sh """
                        mvn sonar:sonar \
                            -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                            -Dsonar.projectName=${SONAR_PROJECT_NAME} \
                            -Dsonar.projectVersion=${SONAR_PROJECT_VERSION} \
                            -Dsonar.sources=src/main/java \
                            -Dsonar.host.url=http://localhost:9000 \
                            -Dsonar.login=${SONAR_LOGIN_TOKEN} \
                            -Dsonar.jacoco.reportPaths=target/jacoco.exec \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                        """
                    }
                }
            }
        }

         stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Upload Artifact to Nexus') {
            steps {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: 'localhost:8081',
                        repository: 'maven-releases-esprithub',
                        groupId: 'tn.esprit',
                        version: pom.version,
                        credentialsId: NEXUS_CREDENTIALS_ID,
                        artifacts: [
                            [
                                artifactId: pom.artifactId,
                                classifier: '',
                                file: 'target/5ARCTIC3-G1-EspritHub.jar',
                                type: 'jar'
                            ]
                        ]
                    )
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_HUB_REPO}:${IMAGE_TAG}")
                }
            }
        }

        stage('Tag Docker Image') {
            steps {
                script {
                    sh "docker tag ${DOCKER_HUB_REPO}:${IMAGE_TAG} ${DOCKER_HUB_REPO}:latest"
                }
            }
        }

        stage('Trivy Scan') {
            steps {
                script {
                    sh "mkdir -p trivy-reports"

                    retry(3) {
                        sh "trivy image --timeout 15m --no-progress --format json -o trivy-reports/trivy-results.json ${DOCKER_HUB_REPO}:${IMAGE_TAG}"
                    }
                }
            }
            post {
                success {
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'trivy-reports',
                        reportFiles: 'trivy-results.json',
                        reportName: 'Trivy Scan Report',
                        reportTitles: 'Trivy Vulnerabilities Report'
                    ])
                }
            }
        }

        stage('Upload Trivy Reports to Nexus') {
            steps {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                        nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: 'localhost:8081',
                        repository: 'maven-releases-esprithub',
                        groupId: 'tn.esprit',
                        version: pom.version,
                        credentialsId: NEXUS_CREDENTIALS_ID,
                        artifacts: [
                            [
                                artifactId: "${pom.artifactId}-trivy-report",
                                classifier: '',
                                file: 'trivy-reports/trivy-results.json',
                                type: 'json'
                            ]
                        ]
                    )
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                        def appImage = docker.image("${DOCKER_HUB_REPO}:${IMAGE_TAG}")
                        appImage.push("${IMAGE_TAG}")
                    }
                }
            }
        }
        stage('Deploy to AKS') {
            steps {
                script {
                    sh '''
                        sudo kubectl apply -f /home/bacembensoltana/terraform-aks/backend-mysql.yaml
                        sudo kubectl rollout status deployment/backend
                        sudo kubectl rollout status deployment/mysql

                        sudo kubectl apply -f /home/bacembensoltana/terraform-aks/frontend.yaml
                        sudo kubectl rollout status deployment/frontend
                    '''
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                sh '''
                    echo "Checking deployments..."
                    kubectl get deployments

                    echo "\nChecking pods..."
                    kubectl get pods

                    echo "\nChecking services..."
                    kubectl get services
                '''
            }
        }
    }
}