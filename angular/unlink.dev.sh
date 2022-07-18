pushd `dirname "${BASH_SOURCE[0]}"` > /dev/null
for d in *; do
	dep="$d/target/dependencies/angular-dependencies/"
	if [ -d $dep ]; then
		rm -rf $dep
	fi
done
popd > /dev/null
