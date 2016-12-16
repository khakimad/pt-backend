/*jshint browser: true*/
/*global define, console*/
define([
  'jquery',
  'underscore',
  'marionette',
  'app',
  'models/users',
  'views/users',
  'models/exercises',
  'views/exercises',
  'models/programs',
  'views/programs',
  'models/certificates',
  'views/certificates',
  'models/goals',
  'views/goals',
  'models/emails',
  'views/emails',
  'views/login'
],
  function($, _, Marionette, App, UsersModels, UsersViews, ExercisesModels, ExercisesViews,
    ProgramsModels, ProgramsViews, CertificatesModels, CertificatesViews,
    GoalsModels, GoalsViews, EmailsModels, EmailsViews, LoginView) {
  'use strict';

    function setupApplicationLayout() {
      var applicationLayout = new UsersViews.Layout();
      var users = new UsersModels.Users();
        var usersView = new UsersViews.Users({
          collection: users
        });
        users.fetch();
        $.get('/api/v1/admin/user-type').done(function(data) {
          users._types = _.union({id: null, nameEn: ''}, data);
          users.trigger('sync');
        });
        users.on('user:view', function(model) {
          var user = new UsersModels.User();
          user._types = users._types;
          if (!_.isUndefined(model)) {
            user.set({
              id: model.get('id'),
              name: model.get('name'),
              email: model.get('email'),
              type: model.get('type'),
              programs: model.get('programs')
            });
          }
          var userEditView = new UsersViews.ViewUserLayout({
            model: user
          });
          user.on('user:back', function() {
            var usersView = new UsersViews.Users({
              collection: users
            });
            users.fetch();
            applicationLayout.mainUsers.show(usersView);
          });
          applicationLayout.mainUsers.show(userEditView);
        });
        users.on('user:new', function(model) {
          var user = new UsersModels.User();
          user._types = users._types;
          if (!_.isUndefined(model)) {
            user.set({
              id: model.get('id'),
              name: model.get('name'),
              email: model.get('email'),
              type: model.get('type'),
              programs: model.get('programs')
            });
          }
          var userEditView = new UsersViews.NewUserLayout({
            model: user
          });
          user.on('user:back', function() {
            var usersView = new UsersViews.Users({
              collection: users
            });
            users.fetch();
            applicationLayout.mainUsers.show(usersView);
          });
          applicationLayout.mainUsers.show(userEditView);
        });
        App.mainRegion.show(applicationLayout);

        applicationLayout.mainUsers.show(usersView);
        
        var exercises = new ExercisesModels.Exercises();
        var exercisesView = new ExercisesViews.Exercises({
          collection: exercises
        });
        exercises.fetch();
        $.get('/api/v1/admin/exercise-bodypart').done(function(data) {
          exercises._bodyparts = _.union({id: null, nameEn: ''}, data);
          exercises.trigger('sync');
        });
        $.get('/api/v1/admin/exercise-equipment-type').done(function(data) {
          exercises._equipmentTypes = _.union({id: null, nameEn: ''}, data);
          exercises.trigger('sync');
        });
        $.get('/api/v1/admin/exercise-type').done(function(data) {
          exercises._types = _.union({id: null, name: ''}, data);
          exercises.trigger('sync');
        });
        $.get('/api/v1/admin/exercise-input').done(function(data) {
          exercises._inputs = _.union({id: null, name: ''}, data);
          exercises.trigger('sync');
        });
        $.get('/api/v1/admin/exercise-output').done(function(data) {
          exercises._outputs = _.union({id: null, name: ''}, data);
          exercises.trigger('sync');
        });
        exercises.on('exercise:new', function(model) {
          var exercise = new ExercisesModels.Exercise();
          exercise._bodyparts = exercises._bodyparts;
          exercise._equipmentTypes = exercises._equipmentTypes;
          exercise._types = exercises._types;
          exercise._inputs = exercises._inputs;
          exercise._outputs = exercises._outputs;
          if (!_.isUndefined(model)) {
            exercise.set({
              id: model.get('id'),
              bodypart: model.get('bodypart'),
              equipmentType: model.get('equipmentType'),
              types: model.get('types'),
              inputs: model.get('inputs'),
              outputs: model.get('outputs'),
              cardioPercent: model.get('cardioPercent'),
              exerciseId: model.get('exerciseId'),
              nameEn: model.get('nameEn'),
              nameNo: model.get('nameNo'),
              descriptionEn: model.get('descriptionEn'),
              descriptionNo: model.get('descriptionNo')
            });
          }
          var exerciseEditView = new ExercisesViews.NewExerciseLayout({
            model: exercise
          });
          exercise.on('exercise:back', function(modelId) {
            exercises._modelId = modelId;
            var exercisesView = new ExercisesViews.Exercises({
              collection: exercises
            });
            exercises.fetch();
            applicationLayout.mainExercises.show(exercisesView);
          });
          applicationLayout.mainExercises.show(exerciseEditView);
        });
        applicationLayout.mainExercises.show(exercisesView);

        var goals = new GoalsModels.Goals();
        var goalsView = new GoalsViews.Goals({
          collection: goals
        });
        goals.fetch();
        $.get('/api/v1/admin/goal-parameter').done(function(data) {
          goals._parameters = _.union({id: null, name: ''}, data);
          goals.trigger('sync');
        });
        goals.on('goal:new', function(model) {
          var goal = new GoalsModels.Goal();
          goal._parameters = goals._parameters;
          if (!_.isUndefined(model)) {
            goal.set({
              id: model.get('id'),
              parameters: model.get('parameters'),
              titleEn: model.get('titleEn'),
              titleNo: model.get('titleNo'),
              title2En: model.get('title2En'),
              title2No: model.get('title2No')
            });
          }
          var goalEditView = new GoalsViews.NewGoalLayout({
            model: goal
          });
          goal.on('goal:back', function(modelId) {
            goals._modelId = modelId;
            var goalsView = new GoalsViews.Goals({
              collection: goals
            });
            goals.fetch();
            applicationLayout.mainGoals.show(goalsView);
          });
          applicationLayout.mainGoals.show(goalEditView);
        });
        applicationLayout.mainGoals.show(goalsView);

        var programs = new ProgramsModels.Programs();
        var programsView = new ProgramsViews.Programs({
          collection: programs
        });
        programs.fetch();
        programs.on('program:new', function(model) {
          var program = new ProgramsModels.Program();
          if (!_.isUndefined(model)) {
            program.set({
              id: model.get('id'),
              name: model.get('name'),
              fileName: model.get('fileName'),
              fileSize: model.get('fileSize'),
              fileType: model.get('fileType'),
              dataUrl: model.get('dataUrl'),
              parseUsers: model.get('parseUsers')
            });
          }
          var programEditView = new ProgramsViews.NewProgramLayout({
            model: program
          });
          program.on('program:back', function() {
            var programsView = new ProgramsViews.Programs({
              collection: programs
            });
            programs.fetch();
            applicationLayout.mainPrograms.show(programsView);
          });
          applicationLayout.mainPrograms.show(programEditView);
        });
        applicationLayout.mainPrograms.show(programsView);

        var certificates = new CertificatesModels.Certificates();
        var certificatesView = new CertificatesViews.Certificates({
          collection: certificates
        });
        certificates.fetch();
        certificates.on('certificate:new', function(model) {
          var certificate = new CertificatesModels.Certificate();
          if (!_.isUndefined(model)) {
            certificate.set({
              id: model.get('id'),
              code: model.get('code'),
              amountOfDays: model.get('amountOfDays'),
              activated: model.get('activated')
            });
          } else {
            certificate.set('code', certificate.generate());
          }
          var certificateEditView = new CertificatesViews.NewCertificateLayout({
            model: certificate
          });
          certificate.on('certificate:back', function() {
            var certificatesView = new CertificatesViews.Certificates({
              collection: certificates
            });
            certificates.fetch();
            applicationLayout.mainCertificates.show(certificatesView);
          });
          applicationLayout.mainCertificates.show(certificateEditView);
        });
        applicationLayout.mainCertificates.show(certificatesView);
        
        var emails = new EmailsModels.Emails();
        var emailsView = new EmailsViews.Emails({
          collection: emails
        });
        emails.fetch();
        $.get('/api/v1/admin/email-message-type').done(function(data) {
          emails._types = _.union({id: null, name: ''}, data);
          emails.trigger('sync');
        });
        emails.on('email:new', function(model) {
          var email = new EmailsModels.Email();
          email._types = emails._types;
          if (!_.isUndefined(model)) {
            email.set({
              id: model.get('id'),
              emailSubjectEn: model.get('emailSubjectEn'),
              emailSubjectNo: model.get('emailSubjectNo'),
              emailTextEn: model.get('emailTextEn'),
              emailTextNo: model.get('emailTextNo'),
              type: model.get('type')
            });
          }
          var emailEditView = new EmailsViews.NewEmailLayout({
            model: email
          });
          email.on('email:back', function() {
            var emailsView = new EmailsViews.Emails({
              collection: emails
            });
            emails.fetch();
            applicationLayout.mainEmails.show(emailsView);
          });
          applicationLayout.mainEmails.show(emailEditView);
        });
        applicationLayout.mainEmails.show(emailsView);
    }

    return Marionette.Controller.extend({
      login: function() {
        // If the user is already logged in,
        // show the default route.
        if (App.globals.user.authorized()) {
          App.vent.trigger('redirect:default');
          return;
        }
        App.mainRegion.show(new LoginView({ model: App.globals.user }));
      },
      index: function () {
        if (!App.globals.user.authorized()) {
          App.vent.trigger('login:required');
          return;
        }
        setupApplicationLayout('');
      }
    });
  });
