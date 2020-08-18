// will place the files into the physical "/dist" folder
// "npm run build" to create the files

var webpack = require('webpack');
var webpackMerge = require('webpack-merge');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var commonConfig = require('./webpack.common.js');
var helpers = require('./helpers');

const ENV = process.env.NODE_ENV = process.env.ENV = 'production';

module.exports = webpackMerge(commonConfig, {
	  // devtool: 'source-map',

	  output: {
		path: helpers.root('dist'),
		publicPath: '', // used for index.html JS files
		filename: '[name].js',
		chunkFilename: '[id].chunk.js'
	  },

	  plugins: [
		new webpack.NoEmitOnErrorsPlugin(),
		new webpack.optimize.UglifyJsPlugin({
		  mangle: {
				keep_fnames: true
		  }
		}),
		new ExtractTextPlugin('[name].css'),new webpack.DefinePlugin({
            'process.env': {
                'ENV': JSON.stringify(ENV)
            }
        }),
		new webpack.LoaderOptionsPlugin({
		  htmlLoader: {
				inimize: false // workaround for ng2
		  }
		})
	  ]
});

