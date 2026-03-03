/**
 * Talent Management - AI Processing Animation
 * Handles the 40-second non-looping animation for CV processing
 */

var codeLines = [
  "► Extracting contact information...",
  "► Parsing technical skills...",
  "► Analyzing work experience...",
  "► Processing education records...",
  "► Evaluating skill proficiency levels...",
  "► Generating AI summary...",
  "► Compiling candidate profile...",
  "► Running final validation..."
];
var currentStep = 0;
var animationTimeouts = [];
var isProcessingComplete = false;
var maxDuration = 50000; // 50 seconds
var stepDuration = maxDuration / codeLines.length; // ~5000ms per step

// Progress steps functionality removed - using simpler status list design

/**
 * Updates the status items display with color coding
 * @param {number} stepIndex - Current step index
 */
function updateCodeLine(stepIndex) {
  if (isProcessingComplete) return;

  var statusItems = document.querySelectorAll('.status-item');

  // Show current step prominently
  statusItems.forEach(function(item, idx) {
    var icon = item.querySelector('.status-icon');
    var text = item.querySelector('.status-text');

    if (idx < stepIndex) {
      // Completed items - show checkmark
      icon.className = 'pi pi-check-circle status-icon status-completed';
      item.classList.add('completed');
      item.classList.remove('active', 'pending');
    } else if (idx === stepIndex) {
      // Current item - show spinner
      icon.className = 'pi pi-spin pi-spinner status-icon status-active';
      item.classList.add('active');
      item.classList.remove('completed', 'pending');
    } else if (idx < codeLines.length) {
      // Future items - show empty circle
      icon.className = 'pi pi-circle status-icon status-pending';
      item.classList.add('pending');
      item.classList.remove('completed', 'active');
    }
  });
}

/**
 * Runs a single animation step and schedules the next one
 * @param {number} stepIndex - Current step index
 */
function runAnimationStep(stepIndex) {
  if (isProcessingComplete || stepIndex >= codeLines.length) {
    // All steps complete, show completion state
    if (!isProcessingComplete) {
      showCompletionState();
    }
    return;
  }

  updateCodeLine(stepIndex);

  // Schedule next step
  var timeout = setTimeout(function() {
    runAnimationStep(stepIndex + 1);
  }, stepDuration);

  animationTimeouts.push(timeout);
}

/**
 * Shows the completion state when all steps are done
 */
function showCompletionState() {
  var statusItems = document.querySelectorAll('.status-item');

  // Mark all status items as complete
  statusItems.forEach(function(item, idx) {
    if (idx < codeLines.length) {
      var icon = item.querySelector('.status-icon');
      icon.className = 'pi pi-check-circle status-icon status-completed';
      item.classList.add('completed');
      item.classList.remove('active', 'pending');
    }
  });
}

/**
 * Starts the AI processing animation
 * Called when CV upload begins
 */
function startAiProcessing() {
  // Reset state
  isProcessingComplete = false;
  currentStep = 0;

  // Clear any existing timeouts
  animationTimeouts.forEach(function(timeout) {
    clearTimeout(timeout);
  });
  animationTimeouts = [];

  // Show dialog
  PF('statusDialog').show();

  // Start animation
  setTimeout(function() {
    runAnimationStep(0);
  }, 300); // Small delay for dialog to render
}

/**
 * Stops the AI processing animation
 * Called when CV upload completes or fails
 */
function stopAiProcessing() {
  isProcessingComplete = true;

  // Clear all pending timeouts
  animationTimeouts.forEach(function(timeout) {
    clearTimeout(timeout);
  });
  animationTimeouts = [];

  // Reload page to reflect new candidate (independent of dialog hide)
  setTimeout(function() {
    window.location.reload();
  }, 1000);
}

/**
 * Renders the skills radar chart using Chart.js
 * Called when the candidate details dialog is shown
 */
var skillsChart = null; // Store chart instance globally
var chartRenderAttempted = false; // Track if we've tried to render

function renderSkillsChart() {
  // Wait a bit for dialog to fully render
  setTimeout(function() {
    var canvas = document.getElementById('skillsRadarChart');
    if (!canvas) {
      console.warn('Skills chart canvas not found');
      return;
    }

    // Destroy existing chart if it exists
    if (skillsChart) {
      skillsChart.destroy();
    }

    // Get skill badges from the DOM to extract data
    var skillBadges = document.querySelectorAll('.skill-badge');
    if (!skillBadges || skillBadges.length === 0) {
      console.warn('No skills found to display');
      return;
    }

    var labels = [];
    var scores = [];

    // Extract skill names and scores from skill badges
    skillBadges.forEach(function(badge) {
      var text = badge.textContent.trim();
      // Extract skill name (before the score)
      var skillName = text.substring(0, text.lastIndexOf('('));
      skillName = skillName.replace('✓', '').trim();

      // Extract score (between parentheses)
      var scoreMatch = text.match(/\((\d+)\/10\)/);
      if (scoreMatch && skillName) {
        labels.push(skillName);
        scores.push(parseInt(scoreMatch[1]));
      }
    });

    // Limit to top 8 skills for better visualization
    if (labels.length > 8) {
      labels = labels.slice(0, 8);
      scores = scores.slice(0, 8);
    }

    var ctx = canvas.getContext('2d');

    // Create radar chart
    skillsChart = new Chart(ctx, {
      type: 'radar',
      data: {
        labels: labels,
        datasets: [{
          label: 'Skill Level',
          data: scores,
          backgroundColor: 'rgba(102, 126, 234, 0.2)',
          borderColor: 'rgba(102, 126, 234, 1)',
          borderWidth: 2,
          pointBackgroundColor: 'rgba(102, 126, 234, 1)',
          pointBorderColor: '#fff',
          pointHoverBackgroundColor: '#fff',
          pointHoverBorderColor: 'rgba(102, 126, 234, 1)',
          pointRadius: 4,
          pointHoverRadius: 6
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: true,
        scales: {
          r: {
            beginAtZero: true,
            max: 10,
            min: 0,
            ticks: {
              stepSize: 2,
              font: {
                size: 11
              }
            },
            pointLabels: {
              font: {
                size: 12,
                weight: '500'
              },
              color: '#333'
            },
            grid: {
              color: 'rgba(0, 0, 0, 0.1)'
            },
            angleLines: {
              color: 'rgba(0, 0, 0, 0.1)'
            }
          }
        },
        plugins: {
          legend: {
            display: false
          },
          tooltip: {
            callbacks: {
              label: function(context) {
                return context.label + ': ' + context.parsed.r + '/10';
              }
            }
          }
        }
      }
    });

    chartRenderAttempted = true;
  }, 300); // Delay to ensure DOM is ready
}

/**
 * Initializes the candidate dialog
 * Sets up tab change listeners to render chart when Skills tab is shown
 */
function initCandidateDialog() {
  chartRenderAttempted = false;

  // Wait for dialog and tabs to render
  setTimeout(function() {
    // Find the tabview element
    var tabView = document.querySelector('.candidate-tabs');
    if (!tabView) {
      console.warn('TabView not found');
      return;
    }

    // Listen for tab changes
    var tabHeaders = tabView.querySelectorAll('.ui-tabs-nav li');
    tabHeaders.forEach(function(tabHeader, index) {
      tabHeader.addEventListener('click', function() {
        // Skills tab is index 1 (second tab)
        if (index === 1 && !chartRenderAttempted) {
          setTimeout(function() {
            renderSkillsChart();
          }, 100);
        }
      });
    });

    // Check if Skills tab is already active (unlikely but possible)
    var activeTab = tabView.querySelector('.ui-tabs-nav li.ui-tabs-selected');
    if (activeTab) {
      var activeIndex = Array.from(tabHeaders).indexOf(activeTab);
      if (activeIndex === 1) {
        renderSkillsChart();
      }
    }
  }, 400);
}
