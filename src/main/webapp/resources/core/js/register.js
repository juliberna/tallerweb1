function showStep(stepNumber) {
    const steps = document.querySelectorAll('.card');
    steps.forEach(step => {
        step.classList.add('d-none');
    });
    document.getElementById('step' + stepNumber).classList.remove('d-none');
}

function nextStep(nextStepNumber) {
    showStep(nextStepNumber);
    document.getElementById('currentStep').value = nextStepNumber;
}

function prevStep(previousStepNumber) {
    showStep(previousStepNumber);
    document.getElementById('currentStep').value = previousStepNumber;
}

document.addEventListener('DOMContentLoaded', function () {
    showStep(1);
});