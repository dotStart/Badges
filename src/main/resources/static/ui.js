/*
 * Copyright 2020 Johannes Donath <johannesd@torchmind.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
'use static';

(function () {
  $.getJSON('/v1/badge', (index) => {
    function getBadgeUri(params, path) {
      for (const parameterName in params) {
        path = path.replace('{' + parameterName + '}', params[parameterName]);
      }

      return location.origin + index.context + path;
    }

    const app = new Vue({
      el: '#app',
      data: {
        systemHealth: '',
        version: null,

        colors: index.colors,
        badgeScopes: index.scopes,

        customColor: 'default',
        customTitle: 'title',
        customValue: 'value'
      },
      methods: {
        getBadgeUri: function (scope, path) {
          const params = {};

          for (const paramName of scope.parameters) {
            params[paramName] = scope.parameterDefaults[paramName];
          }

          return getBadgeUri(params, path);
        },

        getCustomPreviewUri: function (color) {
          return getBadgeUri(
              {
                color: color,
                value: color.toLowerCase()
              },
              '/v1/badge/custom/{color}/color/{value}'
          )
        }
      },
      computed: {
        customBadgeUri: function () {
          return getBadgeUri(
              {
                title: this.customTitle,
                value: this.customValue,
                color: this.customColor
              },
              '/v1/badge/custom/{color}/{title}/{value}'
          )
        }
      }
    });

    M.updateTextFields();

    $.getJSON('/v1/sys/health', (data) => {
      app.version = data.version;
    }).fail((res) => {
      const data = res.responseJSON;
      app.version = data.version;
      app.systemHealth = data.status;
    });
  });
})();
