<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ddUser">
        <h2 class="jh-entity-heading" data-cy="ddUserDetailsHeading">
          <span v-text="$t('chatsubadminApp.ddUser.detail.title')">DdUser</span> {{ ddUser.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.unionid')">Unionid</span>
          </dt>
          <dd>
            <span>{{ ddUser.unionid }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.remark')">Remark</span>
          </dt>
          <dd>
            <span>{{ ddUser.remark }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.userid')">Userid</span>
          </dt>
          <dd>
            <span>{{ ddUser.userid }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.isLeaderInDepts')">Is Leader In Depts</span>
          </dt>
          <dd>
            <span>{{ ddUser.isLeaderInDepts }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.isBoss')">Is Boss</span>
          </dt>
          <dd>
            <span>{{ ddUser.isBoss }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.hiredDate')">Hired Date</span>
          </dt>
          <dd>
            <span>{{ ddUser.hiredDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.isSenior')">Is Senior</span>
          </dt>
          <dd>
            <span>{{ ddUser.isSenior }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.tel')">Tel</span>
          </dt>
          <dd>
            <span>{{ ddUser.tel }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.department')">Department</span>
          </dt>
          <dd>
            <span>{{ ddUser.department }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.workPlace')">Work Place</span>
          </dt>
          <dd>
            <span>{{ ddUser.workPlace }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.orderInDepts')">Order In Depts</span>
          </dt>
          <dd>
            <span>{{ ddUser.orderInDepts }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.mobile')">Mobile</span>
          </dt>
          <dd>
            <span>{{ ddUser.mobile }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.errmsg')">Errmsg</span>
          </dt>
          <dd>
            <span>{{ ddUser.errmsg }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.active')">Active</span>
          </dt>
          <dd>
            <span>{{ ddUser.active }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.avatar')">Avatar</span>
          </dt>
          <dd>
            <span>{{ ddUser.avatar }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.isAdmin')">Is Admin</span>
          </dt>
          <dd>
            <span>{{ ddUser.isAdmin }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.isHide')">Is Hide</span>
          </dt>
          <dd>
            <span>{{ ddUser.isHide }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.jobnumber')">Jobnumber</span>
          </dt>
          <dd>
            <span>{{ ddUser.jobnumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.name')">Name</span>
          </dt>
          <dd>
            <span>{{ ddUser.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.extattr')">Extattr</span>
          </dt>
          <dd>
            <span>{{ ddUser.extattr }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.stateCode')">State Code</span>
          </dt>
          <dd>
            <span>{{ ddUser.stateCode }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.position')">Position</span>
          </dt>
          <dd>
            <span>{{ ddUser.position }}</span>
          </dd>
          <dt>
            <span v-text="$t('chatsubadminApp.ddUser.roles')">Roles</span>
          </dt>
          <dd>
            <span>{{ ddUser.roles }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="ddUser.id" :to="{ name: 'DdUserEdit', params: { ddUserId: ddUser.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./dd-user-details.component.ts"></script>
