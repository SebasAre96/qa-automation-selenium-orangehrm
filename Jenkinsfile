pipeline {
    agent any

    // IMPORTANTE: los nombres deben coincidir con los configurados en
    // Manage Jenkins > Tools (usá los mismos que en el Jenkinsfile de DEMOQA).
    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Navegador')
        string(name: 'TAGS', defaultValue: '@smoke', description: 'Tags de Cucumber (ej: @smoke, @regression, @smoke or @e2e)')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Ejecutar sin interfaz gráfica')
    }

    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Tests') {
            steps {
                sh """
                    mvn -B clean test \
                      -Dbrowser=${params.BROWSER} \
                      -Dheadless=${params.HEADLESS} \
                      -Dcucumber.filter.tags="${params.TAGS}"
                """
            }
        }
    }

    post {
        always {
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
            archiveArtifacts artifacts: 'target/cucumber-reports/**, target/allure-results/**', allowEmptyArchive: true
            script {
                // Requiere el plugin "Allure Jenkins Plugin"; si no está, el build no falla por esto
                try {
                    allure includeProperties: false, results: [[path: 'target/allure-results']]
                } catch (err) {
                    echo "Plugin de Allure no disponible, se omite el reporte: ${err}"
                }
            }
        }
    }
}
