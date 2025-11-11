pipeline {
    agent any

    tools {
        maven 'Maven3'             // Must match Jenkins tool name
        allure 'Allure_2.29.0'     // Must match Jenkins tool name
    }

    environment {
        ALLURE_RESULTS = "allure-results"
        ALLURE_REPORT = "target/site/allure-maven-plugin"
    }

    stages {

        stage('Checkout') {
            steps {
                echo "📦 Checking out code from Git..."
                git branch: 'main', url: 'https://github.com/vinayshsureshram/RestAssuredAPIAutomation.git'
            }
        }

        stage('Build & Test') {
            steps {
                echo "🧪 Running TestNG tests with Maven..."
                // Retry mechanism for transient issues
                retry(2) {
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        sh 'mvn clean test'
                    }
                }
            }
            post {
                always {
                    echo "📁 Archiving Allure results..."
                    archiveArtifacts artifacts: "${ALLURE_RESULTS}/**", fingerprint: true
                }
            }
        }
    }

    // Always run after all stages (even if any fail)
    post {
        always {
            echo "📊 Generating Allure report (always runs)..."
            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                sh 'mvn allure:report || true'
            }

            echo "🚀 Publishing Allure report to Jenkins..."
            allure([
                includeProperties: false,
                jdk: '',
                results: [[path: "${ALLURE_RESULTS}"]],
                reportBuildPolicy: 'ALWAYS'
            ])

            echo "🧹 Cleaning workspace..."
            cleanWs()
        }

        failure {
            echo "❌ Build failed! Check Allure report for screenshots and logs."
        }

        success {
            echo "✅ Build succeeded! Allure report generated at: ${ALLURE_REPORT}"
        }
    }
}