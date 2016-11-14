/*jshint browser: true*/
/*global define, console*/
define([
    'jquery',
    'underscore',
    'marionette',
    'app',
    'bootstrapTab'
],
function ($, _, Marionette, App) {
  'use strict';

  var Layout = Marionette.Layout.extend({
    regions: {
      mainUsers: '#usersMappingConfig'
    },
    template: _.template([
      '<!-- Nav tabs -->',
      '<ul class="nav nav-tabs">',
      '  <li class="active"><a href="#user" data-toggle="tab">Users</a></li>',
      '  <li><a href="#exercise" data-toggle="tab">Exercises</a></li>',
      '  <li><a href="#program" data-toggle="tab" class="js-admin-config">Programs</a></li>',
      '</ul>',
      '<!-- Tab panes -->',
      '<div class="tab-content">',
      '  <div class="tab-pane active" id="user">',
      '    <div id="usersMappingConfig"></div>',
      '  </div>',
      '  <div class="tab-pane" id="exercise">',
      '    <div id="exercisesMappingConfig"></div>',
      '  </div>',
      '  <div class="tab-pane" id="program">',
      '    <div id="programsMappingConfig"></div>',
      '  </div>',
      '</div>'
    ].join(''))
  });
  
  var EmptyView = Marionette.ItemView.extend({
        tagName: 'tr',
        template: _.template('<td colspan="4">There are no users available.</td>')
  });

  var User = Marionette.ItemView.extend({
    tagName: 'tr',
    template: _.template([
      '<td>',
        '{{ id }}',
      '</td>',
      '<td>',
        '{{ name }}',
      '</td>',
      '<td>',
        '<button type="button" class="btn btn-default btn-sm js-edit-value">',
          '<i class="glyphicon glyphicon-edit"></i>',
        '</button>',
      '</td>',
      '<td>',
        '<button type="button" class="btn btn-default btn-sm js-delete-value">',
          '<i class="glyphicon glyphicon-remove"></i>',
        '</button>',
      '</td>'
    ].join('')),

    initialize: function(options) {
      this.collection = options.collection;
    },
    events: {
      'click .js-edit-value': 'editUser',
      'click .js-delete-value': 'deleteUser'
    },
    editUser: function() {
      this.collection.trigger('user:new', this.model);
    },
    deleteUser: function() {
      event.preventDefault();
      var model = this.model;
      var collection = this.collection;
      this.model.destroy()
        .done(function() {
        })
        .fail(function (xhr) {
          App.vent.trigger('xhr:error', 'User ' + model.get('id') + ' delete was failed');
        })
        .always(function() {
          collection.fetch();
        });
    }
  });

  var Users = Marionette.CompositeView.extend({
    itemViewContainer: 'tbody',
    itemView: User,
    emptyView: EmptyView,
    tagName: 'div',
    className: 'js-users-mapping-config',
    ui: {
      table: '.table'
    },
    itemViewOptions : function () {
      return { collection: this.collection };
    },
    initialize: function() {
    },
    template: _.template([
    '<div class="panel panel-primary">',
      '<div class="panel-heading">',
        '<h3 class="panel-title"> Users </h3>',
      '</div>',
      '<button class="btn btn-primary js-new-user" style="margin: 10px;">',
        'New user',
      '</button>',
      '<table class="table">',
        '<thead>',
          '<tr>',
            '<th>ID</th>',
            '<th>Name</th>',
            '<th></th>',
            '<th></th>',
          '</tr>',
        '</thead>',
        '<tbody></tbody>',
      '</table>',
    '</div>'
    ].join('')),
    collectionEvents: {
      'sync': 'render'
    },
    events: {
      'click .js-new-user': 'newUser'
    },
    newUser: function() {
      this.collection.trigger('user:new');
    }
  });

  var NewUserLayout = Marionette.Layout.extend({
    template: _.template([
      '<div class="panel panel-primary">',
        '<div class="panel-heading">',
          '<h3 class="panel-title"> {{ getHeader() }} </h3>',
        '</div>',
        '<div id="buttons"/>',
        '<div id="inputForm"/>',
      '</div>'
    ].join('')),
    templateHelpers: function() {
      var model = this.model;
      return {
        getHeader: function () {
          return model.isNew() ? 'New user' : 'Edit user';
        }
      };
    },
    tagName: 'form',
    className: 'form-horizontal',
    regions: {
      buttons: '#buttons',
      inputForm: '#inputForm'
    },
    onShow: function() {
      this.buttons.show(new NewUserButtons({model: this.model}));
      this.inputForm.show(new NewUserInputForm({model: this.model}));
    }
  });

  var NewUserButtons = Marionette.ItemView.extend({
    template: _.template([
      '<div class="btn-group">',
        '<button class="btn btn-default js-back" style="margin: 10px 0 0 10px;">',
          'Back',
        '</button>',
        '<button class="btn btn-danger js-save  {{ getSaveDisabled() }}" style="margin: 10px 0 0 0; min-width: 100px;">',
          'Save',
        '</button>',
        '<button class="btn btn-default js-discard" style="margin: 10px 0 0 0px;">',
          'Discard',
        '</button>',
      '</div>'
    ].join('')),
    templateHelpers: function() {
      var view = this;
      return {
        getSaveDisabled: function () {
          return view.model.isValid() ? '' : 'disabled';
        }
      };
    },
    events: {
      'click .js-back': 'back',
      'click .js-save': 'save',
      'click .js-discard': 'discard'
    },
    modelEvents: {
      'change': 'render'
    },
    initialize: function(options) {
      this._model = options.model.clone();
    },
    back: function() {
      event.preventDefault();
      this.model.trigger('user:back');
    },
    save: function() {
      event.preventDefault();
      var model = this.model;
      this.model.save().done(function() {
        model.trigger('user:back');
      })
      .fail(function (xhr) {
        App.vent.trigger('xhr:error', 'User save was failed');
      });
    },
    discard: function(event) {
      event.preventDefault();
      this.model.set(this._model.toJSON());
      this.model.trigger('sync');
    }
  });

  var NewUserInputForm = Marionette.ItemView.extend({
    className: 'user-input-form',
    template: _.template([
      '<div class="form-group">',
        '<label class="col-sm-3 control-label">ID</label>',
        '<div class="col-sm-8">',
          '<p class="form-control-static">',
            ' {{ id }}',
          '</p>',
        '</div>',
      '</div>',
      '<div class="form-group">',
        '<label class="col-sm-3 control-label">Name</label>',
        '<div class="col-sm-8">',
          '<textarea id="user-name" class="form-control" rows="3" placeholder="Please enter name" name="address" required="true">',
            '{{ name }}',
          '</textarea>',
        '</div>',
      '</div>'
    ].join('')),
    modelEvents: {
      'sync': 'render',
      'technicians:load': 'render'
    },
    events: {
      'input #user-name': 'inputName'
    },
    ui: {
      name: '#user-name'
    },
    inputName: function() {
      this.model.set('name', this.ui.name.val());
    }
  });

  return {
    Users: Users,
    NewUserLayout: NewUserLayout,
    Layout: Layout
  };

});