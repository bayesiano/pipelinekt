node {
    checkout scm
    echo "Generating Jenkinsfile"
    dir("ci") {
        sh "./gradlew run"
    }
    unstash("generated-ci")
    load("${env.WORKSPACE}/path/to/Jenkinsfile.generated")
}
